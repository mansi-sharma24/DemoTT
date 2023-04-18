package com.example.demott.Modal

data class TimelineInfo(
    val `actual`: String,
    val estimated: String,
    val has_color: Boolean,
    val has_remarks: Boolean,
    val in_time: Boolean,
    val is_cancelled: Boolean,
    val is_hold: Boolean,
    val lines: List<Line>,
    val main_class: String,
    val remarks: String,
    val status: String,
    val status_code: String,
    val status_short: String
)