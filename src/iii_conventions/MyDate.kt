package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)
operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

operator fun MyDate.plus(timeInterval: TimeInterval) = addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) = addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate) {
    operator fun contains(date: MyDate): Boolean {
        return start <= date && date <= endInclusive
    }

    operator fun iterator(): Iterator<MyDate> = DateIterator(this)
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.addTimeIntervals(TimeInterval.DAY, 1)
        return result
    }
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
}
