package com.ichwan.disasterlist.data

import com.google.gson.annotations.SerializedName

data class ResponseData(

    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("result")
    val result: ResultData,
)

data class ResultData(

    @SerializedName("type")
    val type: String,

    @SerializedName("objects")
    val objects: ObjectsData
)

data class ObjectsData (

    @SerializedName("output")
    val output: OutputData
)

data class OutputData(

    @SerializedName("type")
    val type: String,

    @SerializedName("geometries")
    val geometries: List<Geometries>
)

data class Geometries(

    @SerializedName("type")
    val type: String,

    @SerializedName("properties")
    val properties: Properties,

    @SerializedName("coordinates")
    val coordinates: List<Double>
)

data class Properties(

    @SerializedName("disaster_type")
    val disaster_type: String,

    @SerializedName("report_data")
    val report_data: ReportData,

    @SerializedName("tags")
    val tags: Tags,

    @SerializedName("image_url")
    val image_url: String?,

    @SerializedName("title")
    val title: String?
)

data class Tags(

    @SerializedName("instance_region_code")
    val instance_region_code: String
)

data class ReportData(

    @SerializedName("report_type")
    val report_type: String,

    @SerializedName("flood_depth")
    val flood_depth: Int
)
