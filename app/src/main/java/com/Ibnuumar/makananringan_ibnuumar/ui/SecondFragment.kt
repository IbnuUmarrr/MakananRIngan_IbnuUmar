package com.Ibnuumar.makananringan_ibnuumar.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.Ibnuumar.makananringan_ibnuumar.R
import com.Ibnuumar.makananringan_ibnuumar.application.SnacksApp
import com.Ibnuumar.makananringan_ibnuumar.databinding.FragmentSecondBinding
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener  {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val snacksViewModel : SnacksViewModel by viewModels {
        SnacksViewModelFactory((applicationContext as SnacksApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var snacks: Snacks? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang :LatLng?=null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snacks = args.snacks
        if (snacks !=null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(snacks?.name)
            binding.descEditText.setText(snacks?.desc)
            binding.flavorEditText.setText(snacks?.flavor)
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name= binding.nameEditText.text
        val desc= binding.descEditText.text
        val flavor = binding.flavorEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (desc.isEmpty()) {
                Toast.makeText(context, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (flavor.isEmpty()) {
                Toast.makeText(context,  "Rasa tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                if (snacks==null) {
                val snacks = Snacks(0, name.toString(), desc.toString(), flavor.toString(),currentLatLang?.latitude,currentLatLang?.longitude)
                snacksViewModel.insert(snacks)
            }else{
                val snacks = Snacks(snacks?.id!!,name.toString(), desc.toString(),flavor.toString(),currentLatLang?.latitude,currentLatLang?.longitude)
                snacksViewModel.update(snacks)
            }
                findNavController().popBackStack()
            }

        }

        binding.deleteButton.setOnClickListener {
            snacks?.let {  snacksViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        val sydney = LatLng(-34.0 , 151.0)
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }
    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
          applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        )!= PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null){
                    var latlang =LatLng(location.latitude,location.longitude)
                    currentLatLang = latlang
                    var title = "Marker"

                    if (snacks!=null){
                        title=snacks?.name.toString()
                        val newCurrentLocation = LatLng(snacks?.latitude!!,snacks?.longitude!!)
                        latlang=newCurrentLocation
                    }

                    val markerOption =MarkerOptions()
                        .position(latlang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_snack1))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang,15f))
                }
            }
    }
}