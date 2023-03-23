package com.example.healthcare.features.dash.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.databinding.GlossaryCardViewBinding
import com.example.healthcare.features.dash.data.model.GlossaryItem


class GlossaryAdapter(
    private val mGlossary: List<GlossaryItem>,
    private val context: Context
) :
    RecyclerView.Adapter<GlossaryAdapter.GlossaryBaseViewViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GlossaryBaseViewViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = GlossaryCardViewBinding.inflate(inflater, parent, false)

        return GlossaryBaseViewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GlossaryBaseViewViewHolder,
        position: Int
    ) {
        val items = mGlossary[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int {
        return mGlossary.size
    }

    inner class GlossaryBaseViewViewHolder(
        private val binding: GlossaryCardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GlossaryItem) {
            binding.apply {

                glossaryCardTitle.text = item.title
                shortTextView.text =
                    Html.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                fullTextview.text =
                    Html.fromHtml(item.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
                glossaryDate.text = item.date.substring(0, 10)

                readMore.setOnClickListener {
                    readMore.visibility = View.GONE
                    shortTextView.visibility = View.GONE
                    fullTextview.visibility = View.VISIBLE
                    readLess.visibility = View.VISIBLE
                }

                readLess.setOnClickListener {
                    readMore.visibility = View.VISIBLE
                    shortTextView.visibility = View.VISIBLE
                    fullTextview.visibility = View.GONE
                    readLess.visibility = View.GONE
                }

                glossaryLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    val baseUrl = "https://www.healthcare.gov"
                    intent.data = Uri.parse(baseUrl + item.url)
                    context.startActivity(intent)
                }
            }
        }
    }
}
