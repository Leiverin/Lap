package com.poly.lap.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.poly.lap.databinding.ItemBookBinding;
import com.poly.lap.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> mListBook;
    private IOnEventBookListener onEventBook;

    public BookAdapter(List<Book> mListBook, IOnEventBookListener onEventBook) {
        this.mListBook = mListBook;
        this.onEventBook = onEventBook;
    }

    public void setList(List<Book> mListBook){
        this.mListBook = mListBook;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mListBook.get(position);
        holder.binding.tvActor.setText(book.getActor());
        holder.binding.tvName.setText(book.getName());
        holder.binding.tvPrice.setText(book.getPrice()+"$");
        Glide.with(holder.binding.imgBook.getContext()).load(book.getImage()).into(holder.binding.imgBook);
        holder.binding.viewBook.setOnClickListener(view -> {
            onEventBook.onClickView(book);
        });
        holder.binding.imgDelete.setOnClickListener(view -> {
            onEventBook.onDelete(book);
        });
    }

    @Override
    public int getItemCount() {
        return mListBook.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private ItemBookBinding binding;
        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    interface IOnEventBookListener{
        void onClickView(Book book);
        void onDelete(Book book);
    }
}