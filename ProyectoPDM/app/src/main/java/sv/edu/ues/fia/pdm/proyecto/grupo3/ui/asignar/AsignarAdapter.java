package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar;

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
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar.AsignarAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.CoordinadoresAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.CoordinadoresFragment;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.EditarCoordinadoresActivity;

public class AsignarAdapter extends RecyclerView.Adapter<AsignarAdapter.AsignarViewHolder>{

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public AsignarAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public AsignarAdapter.AsignarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.asignar_items, parent, false);
        return new AsignarAdapter.AsignarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsignarAdapter.AsignarViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        //id de coordinador
        String idCoordinador = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idCoordinadorGrupo));

        //id de asignatura
        String idAsignatura = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idAsignaturaGrupo));

        //id de grupo
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idGrupo));


        //cursor coordinador
        Cursor cursorCoord = baseDatosHelper.getCoordinador(idCoordinador);
        //cursor materia
        Cursor cursorMateria = baseDatosHelper.getAsignatura(idAsignatura);

        int indexCodigoMateria = cursorMateria.getColumnIndex(BaseDatosHelper.KEY_codigoAsignatura);
        String materia = cursorMateria.getString(indexCodigoMateria);

        int indexNombreCoordinador = cursorCoord.getColumnIndex(BaseDatosHelper.KEY_nombreCoordinador);
        String nombre = cursorCoord.getString(indexNombreCoordinador);

        holder.textViewCoordinadoresNombre.setText(nombre);
        holder.textViewMaterias.setText(materia);

        // Set click listeners for buttons



        holder.buttonEdit.setOnClickListener(v -> {

            Intent intent = new Intent(mContext, EditarAsignacionActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("nombre", nombre);
            intent.putExtra("materia", materia);
            mContext.startActivity(intent);


        });

        holder.buttonDelete.setOnClickListener(v -> {


            //booton de borrar


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_de_borrar_el_grupo);
            builder.setMessage(mensaje +"\n" + id);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    baseDatosHelper.eliminarGrupoAsignatura(id);

                    Toast.makeText(mContext.getApplicationContext(), R.string.grupo_borrado_correctamente, Toast.LENGTH_SHORT).show();
                    AsignarFragment.getInstance().refreshRecyclerView();
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

    static class AsignarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCoordinadoresNombre;
        TextView textViewMaterias;
        Button buttonEdit;
        Button buttonDelete;

        AsignarViewHolder(View itemView) {
            super(itemView);
            textViewCoordinadoresNombre = itemView.findViewById(R.id.textViewCoordinadorNombre);
            textViewMaterias = itemView.findViewById(R.id.textViewMateria);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}




