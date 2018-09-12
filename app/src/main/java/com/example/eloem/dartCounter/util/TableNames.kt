package com.example.eloem.dartCounter.util

object Games {
    const val tableName = "games"
    const val columnID = "idColumnGames"
    const val columnMode = "modeColumnGames"
    const val columnCurrentPlayerPos = "currentPlayerPosColumnGames"
    const val columnDate = "dateColumn"
}

object Players {
    const val tableName = "players"
    const val columnID = "idColumnPlayers"
    const val columnGameID = "gameIDColumnPlayers"
    const val columnName = "nameColumnPlayers"
    const val columnPoints = "pointsColumnPlayers"
    const val columnPosition = "positionColumnPlayers"
}

object Turns{
    const val tableName = "turns"
    const val columnThrowOne = "throwOneColumn"
    const val columnThrowTwo = "throwTwoColumn"
    const val columnThrowThree = "throwThreeColumn"
    const val columnPlayerID = "playerIDColumn"
    const val columnPosition = "positionColumn"
    const val columnPointsLeft = "pointsLeftColumn"
    const val columnPointsBefore = "pointsBeforeColumn"
}

object Points{
    const val tableName = "points"
    const val columnID = "idColumnPoints"
    const val columnMultiplicator = "multiplicatorColumnPoints"
    const val columnPoint = "pointColumnPoints"
}