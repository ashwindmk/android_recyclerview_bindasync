package com.ashwin.android.recyclerviewbindasync

object DataProvider {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(1, "The Boys", "tmdb_1_theboys", 2019),
            Movie(2, "Kung Fu Panda", "tmdb_2_kungfupanda", 2022),
            Movie(3, "The Walking Dead", "tmdb_3_walkingdead", 2010),
            Movie(4, "Centauro", "tmdb_4_centauro", 2022),
            Movie(5, "The Flash", "tmdb_5_theflash", 2019),
            Movie(6, "Turning Red", "tmdb_6_turningred", 2022),
            Movie(7, "Collision", "tmdb_7_collision", 2022),
            Movie(8, "Spiderhead", "tmdb_8_spiderhead", 2022),
            Movie(9, "Zombies 3", "tmdb_9_zombies3", 2022),
            Movie(10, "Vendetta", "tmdb_10_vendetta", 2022),

            Movie(11, "Ms. Marvel", "tmdb_11_msmarvel", 2022),
            Movie(12, "Sea Beast", "tmdb_12_seabeast", 2022),
            Movie(13, "The Man from Toronto", "tmdb_13_manfrmtoronto", 2022),
            Movie(14, "Hustle", "tmdb_14_hustle", 2022),
            Movie(15, "Lucifer", "tmdb_15_lucifer", 2022),
            Movie(16, "Thor", "tmdb_16_thor", 2011),
            Movie(17, "The Terminal List", "tmdb_17_terminallist", 2022),
            Movie(18, "The Princess", "tmdb_18_princess", 2022),
            Movie(19, "The Scientist", "tmdb_19_thescientist", 2020),

            Movie(20, "Cindrella", "tmdb_20_condrella", 2015),
            Movie(21, "Attack on Titan", "tmdb_21_attackontitan", 2015),
            Movie(22, "The Expendables", "tmdb_22_theexpendables", 2010),
            Movie(23, "Train to Busan", "tmdb_23_traintobusan", 2016),
            Movie(24, "Hercules", "tmdb_24_hercules", 1997),
            Movie(25, "It Follows", "tmdb_25_itfollows", 2014),
            Movie(26, "Wild Card", "tmdb_26_wildcard", 2015),
            Movie(27, "The Lion King", "tmdb_27_thelionking", 1994),
            Movie(28, "Jurassic World", "tmdb_28_jurassicworld", 2022),
            Movie(29, "Lightyear", "tmdb_29_lightyear", 2022),
            Movie(30, "The Black Phone", "tmdb_30_theblackphone", 2022)
        )
    }
}
