package com.ashwin.android.recyclerviewbindasync

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.SystemClock
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

private const val SUB_TAG = "MovieAdapter"

class MovieAdapter(private val context: Context, private val movieList: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView
        val titleTextView: TextView
        val yearTextView: TextView

        init {
            posterImageView = itemView.findViewById<ImageView>(R.id.poster_image_view)
            titleTextView = itemView.findViewById<TextView>(R.id.title_text_view)
            yearTextView = itemView.findViewById<TextView>(R.id.year_text_view)
        }

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            yearTextView.text = movie.year.toString()
        }
    }

    private class AsyncDrawable(resources: Resources?, bitmap: Bitmap?, task: LoadTask) : BitmapDrawable(resources, bitmap) {
        val loadTaskRef: WeakReference<LoadTask>

        val loadTask: LoadTask? get() = loadTaskRef.get()

        init {
            loadTaskRef = WeakReference<LoadTask>(task)
        }
    }

    inner class LoadTask(val movie: Movie, imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        private val imageViewRef: WeakReference<ImageView>

        init {
            imageViewRef = WeakReference(imageView)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            if (isCancelled) return null

            val res = params[0]

            var bitmap: Bitmap? = null

            try {
                // Fetch image from network
                println("${Constant.APP_TAG}: $SUB_TAG: fetching img res = $res")
                SystemClock.sleep(2500L)
                val resId = context.resources.getIdentifier(res, "drawable", context.packageName)
                bitmap = BitmapFactory.decodeResource(context.resources, resId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return bitmap
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            if (isCancelled || bitmap == null) return

            imageViewRef.get()?.let {
                val drawable = it.drawable
                if (drawable is AsyncDrawable) {
                    if (drawable.loadTask == this) {
                        it.setImageBitmap(bitmap)
                    } else {
                        // Another task is loading new image.
                    }
                } else {
                    // Bitmap set through cache, ignore.
                }
            }

            cache.put(movie.id, bitmap)

            super.onPostExecute(bitmap)
        }
    }

    private val cache: LruCache<Long, Bitmap>
    private val placeholder: Bitmap   // or as BitmapDrawable

    init {
        val maxSize = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxSize / 8    // Divide the maximum size by 8 to get a adequate size the LRU cache should reach before it starts to evict bitmaps.
        cache = object : LruCache<Long, Bitmap>(cacheSize) {
            override fun sizeOf(key: Long?, value: Bitmap): Int {
                return value.byteCount / 1024    // returns the size of bitmaps in kB
            }
        }

        placeholder = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie)
        //placeholder = AppCompatResources.getDrawable(context, R.drawable.ic_movie) as BitmapDrawable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val root: View = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(root)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        // Set image
        val bitmap = cache[movie.id]
        if (bitmap != null) {
            holder.posterImageView.setImageBitmap(bitmap)
        } else {
            loadImage(movie, holder.posterImageView)
        }

        // Set other properties
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun loadImage(movie: Movie, imageView: ImageView) {
        if (cancelLoadTask(movie, imageView)) {
            // Nothing was loading or cancelled loading, so load now.
            val loadTask = LoadTask(movie, imageView)
            val asyncDrawable = AsyncDrawable(context.resources, placeholder /*placeholder.bitmap*/, loadTask)
            imageView.setImageDrawable(asyncDrawable)
            loadTask.execute(movie.img)
        } else {
            // Already loading.
        }
    }

    private fun cancelLoadTask(movie: Movie, imageView: ImageView): Boolean {
        val loadTask = getLoadTask(imageView)
        if (loadTask == null) {
            // Consider cancelled and load.
            return true
        } else if (loadTask.movie.id != movie.id) {
            // Loading a different image, cancel.
            loadTask.cancel(true)
            return true
        }

        // Already loading the same image.
        return false
    }

    private fun getLoadTask(imageView: ImageView): LoadTask? {
        val drawable = imageView.drawable
        if (drawable is AsyncDrawable) {
            return drawable.loadTask
        }
        return null
    }
}
