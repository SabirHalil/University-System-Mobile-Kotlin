    package com.example.universitysystem.adapters

    import android.os.Build
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.annotation.RequiresApi
    import androidx.recyclerview.widget.RecyclerView
    import com.example.universitysystem.R
    import com.example.universitysystem.data.models.Announcement
    import com.example.universitysystem.databinding.OneAnnouncementLayoutBinding
    import com.example.universitysystem.utils.extractDateFromDateTime

    class AnnouncementAdapter(private val list:ArrayList<Announcement>):
        RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>() {
            inner class ViewHolder(val binding:OneAnnouncementLayoutBinding):RecyclerView.ViewHolder(binding.root){
                @RequiresApi(Build.VERSION_CODES.O)
                fun bind(announcement: Announcement){
                    binding.tvTitle.text = announcement.title
                    binding.tvDescription.text = announcement.description
                    binding.tvDate.text = binding.root.context.extractDateFromDateTime(announcement.date)
                    binding.ivExpand.setOnClickListener {
                        if (announcement.expanded){
                            binding.ivExpand.setImageResource(R.drawable.ic_expand_more)
                            binding.tvDescription.maxLines = 3
                            binding.tvTitle.maxLines = 1
                        }else{
                            binding.ivExpand.setImageResource(R.drawable.ic_expand_less)
                            binding.tvDescription.maxLines = Int.MAX_VALUE
                            binding.tvTitle.maxLines = Int.MAX_VALUE
                        }
                        announcement.expanded = !announcement.expanded
                    }

                }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(OneAnnouncementLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }