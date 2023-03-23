package com.example.healthcare.features.blog.ui

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

class BlogsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
    }
}