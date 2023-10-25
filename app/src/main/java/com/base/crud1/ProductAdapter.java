package com.base.crud1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductEntity> products;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (products != null) {
            ProductEntity product = products.get(position);
            holder.productName.setText(product.getProductName());
            holder.productPrice.setText(String.valueOf(product.getProductPrice()));
            // Set other views accordingly
            holder.productCategory.setText(product.getProductCategory());
            holder.productDescription.setText(product.getProductDescription());

            // Load the image using Picasso
            Picasso.get()
                    .load(product.getProductImage()) // URL of the image
                    .placeholder(R.drawable.ic_android_black_24dp)
                    .error(R.drawable.ic_android_black_24dp)// Drawable shown if there's an error loading the image
                    .into(holder.productImage); // ImageView to load the image into
        }
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        TextView productCategory;
        TextView productDescription;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_productname);
            productPrice = itemView.findViewById(R.id.tv_productprice);
            productImage = itemView.findViewById(R.id.iv_productimage);
            productCategory = itemView.findViewById(R.id.tv_productcategory);
            productDescription = itemView.findViewById(R.id.tv_productdescription);
            // Initialize other TextViews and Views here
        }
    }
}
