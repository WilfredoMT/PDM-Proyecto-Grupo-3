package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class CicloAdapter extends RecyclerView.Adapter<CicloAdapter.CicloViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public CicloAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public CicloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ciclo_items, parent, false);
        return new CicloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CicloViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nombreCiclo));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idCiclo));

        holder.textViewCicloNombre.setText(nombre);

        // Set click listeners for buttons
        holder.buttonEdit.setOnClickListener(v -> {

            Intent intent = new Intent(mContext, EditarCicloActivity.class);
            intent.putExtra("idEditar", id);
            mContext.startActivity(intent);


            // Handle edit button click
        });

        holder.buttonDelete.setOnClickListener(v -> {

            //booton de borrar

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_de_borrar_el_ciclo);
            builder.setMessage(mensaje +"\n" + nombre);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    baseDatosHelper.eliminarCiclo(id);
                    Toast.makeText(mContext.getApplicationContext(), R.string.ciclo_borrado_correctamente, Toast.LENGTH_SHORT).show();
                    CicloFragment.getInstance().refreshRecyclerView();
                }
            });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Cancelar
                    Toast.makeText(mContext.getApplicationContext(), R.string.accion_cancelada, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null)
            mCursor.close();

        mCursor = newCursor;

        if (newCursor != null)
            notifyDataSetChanged();
    }

    static class CicloViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCicloNombre;
        Button buttonEdit;
        Button buttonDelete;

        CicloViewHolder(View itemView) {
            super(itemView);
            textViewCicloNombre = itemView.findViewById(R.id.textViewCicloNombre);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

}