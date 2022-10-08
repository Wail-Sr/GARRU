package com.example.login_page

import android.R.attr.bitmap
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.example.login_page.databinding.FragmentQrBinding


class QRFragment : Fragment() {

    private lateinit var binding: FragmentQrBinding

    private var id_reservation: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentQrBinding.inflate(layoutInflater)
        val view = binding.root

        id_reservation = arguments?.get("id_reservation") as Int

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val qrgEncoder = QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension)
//        qrgEncoder.setColorBlack(Color.RED)
//        qrgEncoder.setColorWhite(Color.BLUE)
//        try {
//            // Getting QR-Code as Bitmap
//            bitmap = qrgEncoder.getBitmap()
//            // Setting Bitmap to ImageView
//            qrImage.setImageBitmap(bitmap)
//        } catch (e: WriterException) {
//            Log.v(TAG, e.toString())
//        }
//
//        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
//        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
//        val qrgSaver = QRGSaver()
//        qrgSaver.save(
//            binding.qrimage,
//            id_reservation.toString().trim(),
//            bitmap,
//            QRGContents.ImageType.IMAGE_JPEG
//        )

    }

}