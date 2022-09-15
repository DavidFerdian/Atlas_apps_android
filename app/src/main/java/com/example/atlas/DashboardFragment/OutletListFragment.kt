package com.example.atlas.DashboardFragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.atlas.APIRequestHandling.APIRequestClient
import com.example.atlas.DataClass.*
import com.example.atlas.R
import com.example.atlas.RecyclerViewAdapter.CategoriesAdapter
import com.example.atlas.RecyclerViewAdapter.OutletListAdapter
import com.example.atlas.databinding.FragmentBottomNavigationBinding
import com.example.atlas.databinding.FragmentOuletListBinding
import com.google.android.gms.location.*
import com.id.centuryememberproject.util.SnackBarUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class OutletListFragment : Fragment() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private val PERMISSION_ID = 1010
    private lateinit var outletService: Call<OutletResponse>
    lateinit var outletContext: Context
    var binding: FragmentOuletListBinding? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        outletContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (binding == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_oulet_list, container, false
            )
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        RequestPermission()
        /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
             textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
         }*/
        getLastLocation()


        // Inflate the layout for this fragment
        return binding?.root
    }

    fun CheckPermission(): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if (
            ActivityCompat.checkSelfPermission(
                outletContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                outletContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false

    }

    fun RequestPermission() {
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),

            PERMISSION_ID
        )
        Log.i("apanih",PERMISSION_ID.toString())
    }

    fun isLocationEnabled(): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = outletContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    fun getLastLocation(){
        Log.i("permission",CheckPermission().toString())
        if(CheckPermission()){
            if(isLocationEnabled()){
                if (ActivityCompat.checkSelfPermission(
                        outletContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        outletContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        getCityName(location.latitude,location.longitude)
                        getoutletList(location.latitude.toFloat(),location.longitude.toFloat())
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        Log.d("Debug:" ,"Your Location lat:"+ location.latitude)

                    }
                }
            }else{
                Toast.makeText(outletContext,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }
    fun NewLocationData(){
        locationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                outletContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                outletContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
        }
    }
    private fun getCityName(lat: Double,long: Double):String{
        var cityName = ""
        var countryName = ""
        var geoCoder = Geocoder(outletContext, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,1)

        cityName = Adress[0].getAddressLine(0)
        countryName = Adress[0].countryName
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        binding?.etHomeSearchBar?.setText(cityName)
        return cityName
    }

    private fun getoutletList(lat: Float, lon: Float) {
        binding!!.progressBar.visibility = View.VISIBLE
        binding!!.rvOutletList.visibility = View.GONE
        outletService =
            APIRequestClient.outletService(outletContext)
                .getOutletList(OutletBody(lon,lat))


        outletService.enqueue(object : Callback<OutletResponse> {
            override fun onFailure(call: Call<OutletResponse>, t: Throwable) {
                if (!call.isCanceled) {
                    SnackBarUtility.renderSnackBar(
                        binding!!.baseLayout,
                        outletContext.getString(R.string.api_request_failed_message)
                    )
                }
            }

            override fun onResponse(
                call: Call<OutletResponse>,
                response: Response<OutletResponse>
            ) {
                binding?.progressBar?.visibility = View.GONE

                Log.i("munculapa",response.toString() )
                if (response.code() == 200 && response.body()?.data != null) {
                    binding!!.rvOutletList.visibility = View.VISIBLE
                    with(binding!!.rvOutletList) {
                        layoutManager =
                            GridLayoutManager(
                                (outletContext),
                                1,
                                GridLayoutManager.VERTICAL,
                                false
                            )
                        adapter = setupListoutletAdapter(response.body()?.data)
                    }

                    binding?.baseLayout?.visibility = View.VISIBLE
                    binding?.rvOutletList?.recycledViewPool?.setMaxRecycledViews(0, 0)
                } else {
                    binding?.rvOutletList?.visibility = View.GONE
              }

            }
        })
    }
    private fun setupListoutletAdapter(product: List<DataItemoutlet>?): OutletListAdapter? {
        return product?.let {
            OutletListAdapter(
                outletContext ,
                it,
            )
        }
    }
}