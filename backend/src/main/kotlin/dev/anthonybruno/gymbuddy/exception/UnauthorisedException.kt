package dev.anthonybruno.gymbuddy.exception

class UnauthorisedException(message: String) : HttpException(401, message)
