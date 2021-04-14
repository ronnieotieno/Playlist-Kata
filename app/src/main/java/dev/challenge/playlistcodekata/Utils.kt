package dev.challenge.playlistcodekata

/**
 * Kotlin inline fun to add all the [Long] present in a collection
 */
inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

const val NOTIFY_USER_LIMIT = "You have reached the limit, sign up for pro to get more storage"
const val NOTIFY_PRO_USER_LIMIT = "You have reached the limit"
const val NOTIFY_ERROR_OCCURRED = "Something went wrong try again"
const val TASK_SUCCESSFUL = "Task successful"