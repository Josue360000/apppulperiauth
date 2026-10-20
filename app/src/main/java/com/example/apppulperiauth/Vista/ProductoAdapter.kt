package com.example.apppulperiauth.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppulperiauth.Modelo.Producto
import com.example.apppulperiauth.R
import java.util.Locale

class ProductoAdapter(
    private val onEditar: ((Producto) -> Unit)? = null,
    private val onEliminar: ((Producto) -> Unit)? = null
) : RecyclerView.Adapter<ProductoAdapter.VH>() {

    private var lista: List<Producto> = emptyList()

    fun setLista(nueva: List<Producto>?) {
        lista = nueva ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = lista[position]
        holder.tvNombre.text = p.name
        val codigo = p.barcode ?: "-"
        val valorFormateado = String.format(Locale.getDefault(), "%.2f", p.getValorTotal())
        holder.tvDetalle.text =
            "Precio: L.${p.price} | Stock: ${p.stock} | Codigo: $codigo | Valor: L.$valorFormateado"

        onEditar?.let { callback ->
            holder.itemView.setOnClickListener { callback(p) }
        }
        onEliminar?.let { callback ->
            holder.itemView.setOnLongClickListener {
                callback(p)
                true
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvItemNombre)
        val tvDetalle: TextView = itemView.findViewById(R.id.tvItemDetalle)
    }
}