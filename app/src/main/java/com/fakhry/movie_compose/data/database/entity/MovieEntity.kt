package com.fakhry.movie_compose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fakhry.movie_compose.core.utils.BASE_BACKDROP_URL
import com.fakhry.movie_compose.core.utils.BASE_POSTER_URL
import com.fakhry.movie_compose.domain.model.Movie
import com.fakhry.movie_compose.domain.model.MovieDetails

@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_movie")
    var movieId: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String?,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double
) {
    fun toMovie() = Movie(
        id = movieId,
        posterPath = if (posterPath != null) "$BASE_POSTER_URL$posterPath" else "",
        title = title,
        releaseYear = releaseDate.substring(0, 4),
        overview = overview,
        rating = voteAverage,
    )

    fun toMovieDetails() = MovieDetails(
        id = movieId,
        title = title,
        overview = overview,
        posterPath = if (posterPath != null) "$BASE_POSTER_URL$posterPath" else "",
        releaseYear = releaseDate.substring(0, 4),
        backdropPath = if (backdropPath != null) "$BASE_BACKDROP_URL$backdropPath" else "",
        voteAverage = voteAverage,
    )

    companion object {
        fun generateDummyMovieEntity(): List<MovieEntity> {
            return listOf(
                MovieEntity(
                    movieId = 436270,
                    title = "Black Adam",
                    releaseDate = "2022-10-19",
                    overview = "Nearly 5,000 years after he was bestowed with the almighty powers of the Egyptian gods—and imprisoned just as quickly—Black Adam is freed from his earthly tomb, ready to unleash his unique form of justice on the modern world.",
                    posterPath = "/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
                    backdropPath = "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
                    voteAverage = 7.3,
                ),
                MovieEntity(
                    movieId = 1013860,
                    title = "R.I.P.D. 2: Rise of the Damned",
                    releaseDate = "2022-11-15",
                    overview = "When Sheriff Roy Pulsipher finds himself in the afterlife, he joins a special police force and returns to Earth to save humanity from the undead.",
                    posterPath = "/g4yJTzMtOBUTAR2Qnmj8TYIcFVq.jpg",
                    backdropPath = "/kmzppWh7ljL6K9fXW72bPN3gKwu.jpg",
                    voteAverage = 6.8,
                ),
                MovieEntity(
                    movieId = 829799,
                    title = "Paradise City",
                    releaseDate = "2022-11-11",
                    overview = "Renegade bounty hunter Ryan Swan must carve his way through the Hawaiian crime world to wreak vengeance on the kingpin who murdered his father.",
                    posterPath = "/uGuHHS9SWv7MrFhCH6zoGGd7DA8.jpg",
                    backdropPath = "/au4HUSWDRadIcl9CqySlw1kJMfo.jpg",
                    voteAverage = 6.3,
                ),
                MovieEntity(
                    movieId = 872177,
                    title = "Corrective Measures",
                    releaseDate = "2022-04-29",
                    overview = "Set in San Tiburon, the world's most dangerous maximum-security penitentiary and home to the world's most treacherous superpowered criminals, where tensions among the inmates and staff heighten, leading to anarchy that engulfs the prison and order is turned upside down.",
                    posterPath = "/aHFq9NMhavOL0jtQvmHQ1c5e0ya.jpg",
                    backdropPath = "/8Tr79lfoCkOYRg8SYwWit4OoQLi.jpg",
                    voteAverage = 5.0,
                ),
                MovieEntity(
                    movieId = 988233,
                    title = "Hex",
                    releaseDate = "2022-11-01",
                    overview = "Following a mysterious disappearance on a jump, a group of skydivers experience paranormal occurrences that leave them fighting for their lives.",
                    posterPath = "/xFJHb43ZAnnuiDztxZYsmyopweb.jpg",
                    backdropPath = "/90ZZIoWQLLEXSVm0ik3eEQBinul.jpg",
                    voteAverage = 4.3,
                ),
                MovieEntity(
                    movieId = 724495,
                    title = "The Woman King",
                    releaseDate = "2022-09-15",
                    overview = "The story of the Agojie, the all-female unit of warriors who protected the African Kingdom of Dahomey in the 1800s with skills and a fierceness unlike anything the world has ever seen, and General Nanisca as she trains the next generation of recruits and readies them for battle against an enemy determined to destroy their way of life.",
                    posterPath = "/438QXt1E3WJWb3PqNniK0tAE5c1.jpg",
                    backdropPath = "/7zQJYV02yehWrQN6NjKsBorqUUS.jpg",
                    voteAverage = 7.9,
                ),
                MovieEntity(
                    movieId = 862965,
                    title = "Emily the Criminal",
                    releaseDate = "2022-08-12",
                    overview = "Emily, who is saddled with student debt and locked out of the job market due to a minor criminal record, gets involved in a credit card scam that pulls her into the criminal underworld of Los Angeles, ultimately leading to deadly consequences.",
                    posterPath = "/iZvzMpREGiqDQ5eYbx8z70qPgst.jpg",
                    backdropPath = "/gVecEIEZLZg3pV5CACXAB48I6au.jpg",
                    voteAverage = 6.9,
                ),
                MovieEntity(
                    movieId = 948276,
                    title = "Balle perdue 2",
                    releaseDate = "2022-11-10",
                    overview = "Having cleared his name, genius mechanic Lino has only one goal in mind: getting revenge on the corrupt cops who killed his brother and his mentor.",
                    posterPath = "/uAeZI1JJbLPq7Bu5dziH7emHeu7.jpg",
                    backdropPath = "/707thQazLJiYLBhCrZlRoV05NNL.jpg",
                    voteAverage = 6.8,
                ),
                MovieEntity(
                    movieId = 934641,
                    title = "The Minute You Wake Up Dead",
                    releaseDate = "2022-11-04",
                    overview = "A stockbroker in a small southern town gets embroiled in an insurance scam with a next-door neighbor that leads to multiple murders when a host of other people want in on the plot. Sheriff Thurmond Fowler, the by-the-book town sheriff for over four decades, works earnestly to try and unravel the town’s mystery and winds up getting more than he bargained for.",
                    posterPath = "/pUPwTbnAqfm95BZjNBnMMf39ChT.jpg",
                    backdropPath = "/sP1ShE4BGLkHSRqG9ZeGHg6C76t.jpg",
                    voteAverage = 4.9,
                ),
                MovieEntity(
                    movieId = 830784,
                    title = "Lyle, Lyle, Crocodile",
                    releaseDate = "2022-10-07",
                    overview = "When the Primm family moves to New York City, their young son Josh struggles to adapt to his new school and new friends. All of that changes when he discovers Lyle — a singing crocodile who loves baths, caviar and great music — living in the attic of his new home. But when Lyle’s existence is threatened by evil neighbor Mr. Grumps, the Primms must band together to show the world that family can come from the most unexpected places.",
                    posterPath = "/irIS5Tn3TXjNi1R9BpWvGAN4CZ1.jpg",
                    backdropPath = "/c1bz69r0v065TGFA5nqBiKzPDys.jpg",
                    voteAverage = 7.7,
                ),
                MovieEntity(
                    movieId = 338958,
                    title = "Disenchanted",
                    releaseDate = "2022-11-16",
                    overview = "Disillusioned with life in the city, feeling out of place in suburbia, and frustrated that her happily ever after hasn’t been so easy to find, Giselle turns to the magic of Andalasia for help. Accidentally transforming the entire town into a real-life fairy tale and placing her family’s future happiness in jeopardy, she must race against time to reverse the spell and determine what happily ever after truly means to her and her family.",
                    posterPath = "/4x3pt6hoLblBeHebUa4OyiVXFiM.jpg",
                    backdropPath = "/kpUre8wWSXn3D5RhrMttBZa6w1v.jpg",
                    voteAverage = 7.3,
                ),
                MovieEntity(
                    movieId = 899294,
                    title = "Frank and Penelope",
                    releaseDate = "2022-06-03",
                    overview = "A tale of love and violence when a man on his emotional last legs finds a savior seductively dancing in a run-down strip club. And a life most certainly headed off a cliff suddenly becomes redirected - as everything is now worth dying for.",
                    posterPath = "/5NpXoAi3nEQkEgLO09nmotPfyNa.jpg",
                    backdropPath = "/eyiSLRh44SKKWIJ6bxWq8z1sscB.jpg",
                    voteAverage = 7.5,
                ),
                MovieEntity(
                    movieId = 846778,
                    title = "Margaux",
                    releaseDate = "2022-09-09",
                    overview = "As a group of seniors celebrate their final college days at a smart house, the house's highly advanced AI system, Margaux, begins to take on a deadly presence of her own. A carefree weekend of partying turns into a dystopian nightmare as they realize Margaux's plans to eliminate her tenants one way or another. Time begins to run out as the group desperately tries to survive and outsmart the smart home.",
                    posterPath = "/uNzgeMetu9l4q9NDw7gtiUFwPOJ.jpg",
                    backdropPath = "/sCOHkah9RbFeZfFnfBrcykKCMNa.jpg",
                    voteAverage = 6.8,
                ),
                MovieEntity(
                    movieId = 979924,
                    title = "On the Line",
                    releaseDate = "2022-10-31",
                    overview = "A provocative and edgy radio host must play a dangerous game of cat and mouse with a mysterious caller who's kidnapped his family and is threatening to blow up the whole station.",
                    posterPath = "/a14BbSHvLgzCdbDD6tAL8OBVKL1.jpg",
                    backdropPath = "/26D4G7okoLYbDf5MFYFlSRM6jMv.jpg",
                    voteAverage = 6.5,
                ),
                MovieEntity(
                    movieId = 505642,
                    title = "Black Panther: Wakanda Forever",
                    releaseDate = "2022-11-09",
                    overview = "Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation from intervening world powers in the wake of King T’Challa’s death. As the Wakandans strive to embrace their next chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path for the kingdom of Wakanda.",
                    posterPath = "/ps2oKfhY6DL3alynlSqY97gHSsg.jpg",
                    backdropPath = "/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
                    voteAverage = 7.5,
                ),
                MovieEntity(
                    movieId = 966220,
                    title = "Снайпер. Білий ворон",
                    releaseDate = "2022-05-03",
                    overview = "Mykola is an eccentric pacifist who wants to be useful to humanity. When the war begins at Donbass, Mykola’s naive world is collapsing as the militants kill his pregnant wife and burn his home to the ground. Recovered, he makes a cardinal decision and gets enlisted in a sniper company. Having met his wife’s killers, he emotionally breaks down and arranges “sniper terror” for the enemy. He’s saved from a senseless death by his instructor who himself gets mortally wounded. The death of a friend leaves a “scar” and Mykola is ready to sacrifice his life.",
                    posterPath = "/lZOODJzwuQo0etJJyBBZJOSdZcW.jpg",
                    backdropPath = "/yNib9QAiyaopUJbaayKQ2xK7mYf.jpg",
                    voteAverage = 7.5,
                ),
                MovieEntity(
                    movieId = 551271,
                    title = "Medieval",
                    releaseDate = "2022-09-08",
                    overview = "The story of 14th century Czech icon and warlord Jan Zizka who defeated armies of the Teutonic Order and the Holy Roman Empire.",
                    posterPath = "/4njdAkiBdC5LnFApeXSkFQ78GdT.jpg",
                    backdropPath = "/AokFVAl1JVooW1uz2V2vxNUxfit.jpg",
                    voteAverage = 7.2,
                ),
                MovieEntity(
                    movieId = 882598,
                    title = "Smile",
                    releaseDate = "2022-09-23",
                    overview = "After witnessing a bizarre, traumatic incident involving a patient, Dr. Rose Cotter starts experiencing frightening occurrences that she can't explain. As an overwhelming terror begins taking over her life, Rose must confront her troubling past in order to survive and escape her horrifying new reality.",
                    posterPath = "/aPqcQwu4VGEewPhagWNncDbJ9Xp.jpg",
                    backdropPath = "/olPXihyFeeNvnaD6IOBltgIV1FU.jpg",
                    voteAverage = 6.8,
                ),
                MovieEntity(
                    movieId = 939210,
                    title = "Blue's Big City Adventure",
                    releaseDate = "2022-11-18",
                    overview = "When Josh gets the opportunity of a lifetime to audition for Rainbow Puppy’s Broadway musical, Josh and Blue skidoo to NYC for the very first time where they meet new friends and discover the magic of music, dance, and following one’s dreams.",
                    posterPath = "/gFyB6XHUrvd3FvgN7NQbS0gjYv7.jpg",
                    backdropPath = "/17sIiv4LGapYnyvdpKxA1vozBqY.jpg",
                    voteAverage = 7.5,
                ),
                MovieEntity(
                    movieId = 668461,
                    title = "Slumberland",
                    releaseDate = "2022-11-09",
                    overview = "A young girl discovers a secret map to the dreamworld of Slumberland, and with the help of an eccentric outlaw, she traverses dreams and flees nightmares, with the hope that she will be able to see her late father again.",
                    posterPath = "/f18kgonrgr8YvEuvshExS4XBGL8.jpg",
                    backdropPath = "/o3mothJXDR9vLlZHpwyfFCycCNx.jpg",
                    voteAverage = 7.9,
                ),
            ).sortedBy { it.movieId }
        }
    }
}