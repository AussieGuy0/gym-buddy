package dev.anthonybruno.gymbuddy.exception

class BadRequestException(message: String) : HttpException(400, message)
