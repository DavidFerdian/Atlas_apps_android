package com.example.atlas.DataClass

import com.google.gson.annotations.SerializedName
import com.id.centuryememberproject.dataClass.defaultResponse.DefaultResponseError

data class ProductResponseList(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: DefaultResponseError
)


data class ProductDataItem(

	@field:SerializedName("ProductTotalVisited")
	val productTotalVisited: Int? = null,

	@field:SerializedName("ProductDiscountPercentage")
	val productDiscountPercentage: Int? = null,

	@field:SerializedName("ProductCustomSellPackName")
	val productCustomSellPackName: String? = null,

	@field:SerializedName("ProductSellPack")
	val productSellPack: Int? = null,

	@field:SerializedName("ProductCustomSellPack")
	val productCustomSellPack: Int? = null,

	@field:SerializedName("ProductPrice")
	val productPrice: Int? = null,

	@field:SerializedName("ProductPricePDN")
	val productPricePDN: Int? = null,

	@field:SerializedName("ProductDetailImage")
	val productDetailImage: String? = null,

	@field:SerializedName("ProductTotalSales")
	val productTotalSales: Int? = null,

	@field:SerializedName("ProductPrescriptionYN")
	val productPrescriptionYN: String? = null,

	@field:SerializedName("ProductLastUpdate")
	val productLastUpdate: String? = null,

	@field:SerializedName("ProductSellUnit")
	val productSellUnit: Int? = null,

	@field:SerializedName("ProductPriceMin")
	val productPriceMin: Int? = null,

	@field:SerializedName("ProductActiveYN")
	val productActiveYN: String? = null,

	@field:SerializedName("ProductMaxPurchase")
	val productMaxPurchase: Int? = null,

	@field:SerializedName("ProductMainImage")
	val productMainImage: String? = null,

	@field:SerializedName("ProductPriceMax")
	val productPriceMax: Int? = null,

	@field:SerializedName("ProductGenerikName")
	val productGenerikName: String? = null,

	@field:SerializedName("ProductMedPack")
	val productMedPack: String? = null,

	@field:SerializedName("ProductMedunit")
	val productMedunit: Int? = null,

	@field:SerializedName("ProductUnArchiveUser")
	val productUnArchiveUser: String? = null,

	@field:SerializedName("ProductQtyBundling")
	val productQtyBundling: String? = null,

	@field:SerializedName("ProductSellPackName")
	val productSellPackName: String? = null,

	@field:SerializedName("ProductMaxQtyOrder")
	val productMaxQtyOrder: Int? = null,

	@field:SerializedName("ProductArchiveDate")
	val productArchiveDate: String? = null,

	@field:SerializedName("ProductID")
	val productID: String? = null,

	@field:SerializedName("ProductSideImage")
	val productSideImage: String? = null,

	@field:SerializedName("ProductArchiveUser")
	val productArchiveUser: String? = null,

	@field:SerializedName("ProductFrontImage")
	val productFrontImage: String? = null,

	@field:SerializedName("ProductTopImage")
	val productTopImage: String? = null,

	@field:SerializedName("ProductWeightUnit")
	val productWeightUnit: Int? = null,

	@field:SerializedName("ProductCustomSellUnit")
	val productCustomSellUnit: Int? = null,

	@field:SerializedName("ProductUnArchiveDate")
	val productUnArchiveDate: String? = null,

	@field:SerializedName("ProductName2")
	val productName2: String? = null,

	@field:SerializedName("ProductNIE")
	val productNIE: String? = null,

	@field:SerializedName("ProductOutlet")
	val productOutlet: Any? = null,

	@field:SerializedName("ProductArchiveYN")
	val productArchiveYN: String? = null,

	@field:SerializedName("ProductName1")
	val productName1: String? = null,

	@field:SerializedName("ProductDescription")
	val productDescription: List<ProductDescriptionItem?>? = null,

	@field:SerializedName("ProductVisitedDate")
	val productVisitedDate: String? = null,

	@field:SerializedName("ProductWeightTotal")
	val productWeightTotal: Int? = null
)

data class ProductDescriptionItem(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Criteria")
	val criteria: String? = null
)
