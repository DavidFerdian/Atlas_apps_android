package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponseError

data class CategoriesListResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: DefaultResponseError
)

data class DataItem(

	@field:SerializedName("category_img_name")
	val categoryImgName: String? = null,

	@field:SerializedName("category_atlas_id")
	val categoryAtlasId: String? = null,

	@field:SerializedName("category_menu_upload_date")
	val categoryMenuUploadDate: String? = null,

	@field:SerializedName("category_project_id")
	val categoryProjectId: String? = null,

	@field:SerializedName("category_last_update")
	val categoryLastUpdate: String? = null,

	@field:SerializedName("category_info_solution_updated_by")
	val categoryInfoSolutionUpdatedBy: String? = null,

	@field:SerializedName("category_img")
	val categoryImg: String? = null,

	@field:SerializedName("category_info_solution")
	val categoryInfoSolution: String? = null,

	@field:SerializedName("category_menu_show_yn")
	val categoryMenuShowYn: String? = null,

	@field:SerializedName("category_description")
	val categoryDescription: String? = null,

	@field:SerializedName("category_menu_showed_by")
	val categoryMenuShowedBy: String? = null,

	@field:SerializedName("category_info_solution_updated_date")
	val categoryInfoSolutionUpdatedDate: String? = null,

	@field:SerializedName("category_menu_start_date")
	val categoryMenuStartDate: String? = null,

	@field:SerializedName("category_menu_id")
	val categoryMenuId: String? = null,

	@field:SerializedName("category_main_menu")
	val categoryMainMenu: String? = null,

	@field:SerializedName("category_group_id")
	val categoryGroupId: String? = null
)

data class Error(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
