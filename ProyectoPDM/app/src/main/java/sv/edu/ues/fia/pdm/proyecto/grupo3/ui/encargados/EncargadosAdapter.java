package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosFragment;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EditarEncargadosActivity;

public class EncargadosAdapter extends RecyclerView.Adapter<EncargadosAdapter.EncargadosViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public EncargadosAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public EncargadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.encargados_items, parent, false);
        return new EncargadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  EncargadosViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nombreEncargadoHorario));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idEncargadoHorario));
        String idUsuario = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idUsuarioEncargadoHorario));

        holder.textViewEncargadosNombre.setText(nombre);

        // Set click listeners for buttons



        holder.buttonEdit.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, EditarEncargadosActivity.class);
            intent.putExtra("idEditar", id);
            mContext.startActivity(intent);


        });





        holder.buttonDelete.setOnClickListener(v -> {


            //booton de borrar

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_de_que_quieres_borrar_el_encargado_de_horario);
            builder.setMessage(mensaje +"\n" + nombre);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    baseDatosHelper.eliminarEncargado(id);
                    baseDatosHelper.eliminarUsuario(idUsuario);
                    Toast.makeText(mContext.getApplicationContext(), R.string.encargado_de_horario_borrado_correctamente, Toast.LENGTH_SHORT).show();
                    EncargadosFragment.getInstance().refreshRecyclerView();
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

    static class EncargadosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEncargadosNombre;
        Button buttonEdit;
        Button buttonDelete;

        EncargadosViewHolder(View itemView) {
            super(itemView);
            textViewEncargadosNombre = itemView.findViewById(R.id.textViewEncargadosNombre);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}