package shohjahon.salaryuz.models

import com.google.gson.annotations.SerializedName

data class Ishchi(
    @SerializedName("surname") var surname: String,
    @SerializedName("name") var name: String,
    @SerializedName("position") var position: String,
    @SerializedName("bonus") var bonus: String,
    @SerializedName("salary") var salary: String
)