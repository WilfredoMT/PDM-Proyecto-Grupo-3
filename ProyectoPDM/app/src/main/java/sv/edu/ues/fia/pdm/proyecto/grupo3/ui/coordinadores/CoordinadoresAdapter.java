package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores;

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

public class CoordinadoresAdapter extends RecyclerView.Adapter<CoordinadoresAdapter.CoordinadoresViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public CoordinadoresAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public CoordinadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.coordinador_items, parent, false);
        return new CoordinadoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinadoresViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nombreCoordinador));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idCoordinador));
        String idUsuario = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idUsuarioCoordinador));

        holder.textViewCoordinadoresNombre.setText(nombre);

        // Set click listeners for buttons



        holder.buttonEdit.setOnClickListener(v -> {


            Intent intent = new Intent(mContext, EditarCoordinadoresActivity.class);
            intent.putExtra("idEditar", id);
            mContext.startActivity(intent);


        });





        holder.buttonDelete.setOnClickListener(v -> {


            //booton de borrar

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_de_borrar_el_coordinador);
            builder.setMessage(mensaje +"\n" + nombre);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    baseDatosHelper.eliminarCoordinador(id);
                    baseDatosHelper.eliminarUsuario(idUsuario);
                    Toast.makeText(mContext.getApplicationContext(), R.string.coordinador_borrando_correctamente, Toast.LENGTH_SHORT).show();
                    CoordinadoresFragment.getInstance().refreshRecyclerView();
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

    static class CoordinadoresViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCoordinadoresNombre;
        Button buttonEdit;
        Button buttonDelete;

        CoordinadoresViewHolder(View itemView) {
            super(itemView);
            textViewCoordinadoresNombre = itemView.findViewById(R.id.textViewCoordinadorNombre);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}