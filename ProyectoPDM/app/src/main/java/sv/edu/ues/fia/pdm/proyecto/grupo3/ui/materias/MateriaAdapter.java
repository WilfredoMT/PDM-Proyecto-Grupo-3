package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias;

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

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder > {

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public MateriaAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View views = inflater.inflate(R.layout.materias_items, parent, false);
        return new MateriaViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaAdapter.MateriaViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        String codigo = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_codigoAsignatura));
        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nombreAsignatura));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idAsignatura));

        holder.textViewMateriaCodigo.setText(codigo);

        // Set click listeners for buttons
        holder.buttonEdit.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, EditarMateriaActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);





            // Handle edit button click
        });

        holder.buttonDelete.setOnClickListener(v -> {

            //booton de borrar

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_de_borrar_la_materia);
            builder.setMessage(mensaje +"\n" + nombre);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    baseDatosHelper.eliminarAsignatura(id);
                    Toast.makeText(mContext.getApplicationContext(), R.string.materia_borrada_correctamente, Toast.LENGTH_SHORT).show();
                    MateriasFragment.getInstance().refreshRecyclerView();
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
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null)
            mCursor.close();

        mCursor = newCursor;

        if (newCursor != null)
            notifyDataSetChanged();
    }

    static class MateriaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMateriaCodigo;
        Button buttonEdit;
        Button buttonDelete;

        MateriaViewHolder(View itemView) {
            super(itemView);
            textViewMateriaCodigo = itemView.findViewById(R.id.textViewMateriasCodigo);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

}


