package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
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

public class RespuestaSolicitudesAdapter extends RecyclerView.Adapter<RespuestaSolicitudesAdapter.SolicitudesHorarioViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    public String nombreUsuario;
    private BaseDatosHelper baseDatosHelper;

    public RespuestaSolicitudesAdapter(Context context, Cursor cursor, String nomUsuario) {
        mContext = context;
        mCursor = cursor;
        nombreUsuario = nomUsuario;

    }

    @NonNull
    @Override
    public RespuestaSolicitudesAdapter.SolicitudesHorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.respuesta_solicitudes_items, parent, false);
        return new RespuestaSolicitudesAdapter.SolicitudesHorarioViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RespuestaSolicitudesAdapter.SolicitudesHorarioViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;


        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        Cursor cursor = baseDatosHelper.getUsuario(nombreUsuario);
        String idUsuarioLogueado = cursor.getString(0);


        cursor = baseDatosHelper.getCoordinador(idUsuarioLogueado, null, null);
        String idCoordinadorLogueado = cursor.getString(0);
        String nombreCoordinadorLogueado = cursor.getString(1);


        String nomEvento = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nomEvento));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idEvento));

        Cursor cursor3 = baseDatosHelper.getEvento(id);
        String idPrioridad = cursor3.getString(4);
        String idHorario = cursor3.getString(3);
        String idPropuesta = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idPropuestaEvento));
        Cursor cursor2 = baseDatosHelper.getPropuesta(idPropuesta);
        String idCoordinador = cursor2.getString(cursor2.getColumnIndexOrThrow(BaseDatosHelper.KEY_idCoordinadorPropuesta));
        Log.e("idCoordinadorEncontrado", idCoordinador);
        Log.e("idCoordinadorLogueado", idCoordinadorLogueado);

        int idCoord = Integer.parseInt(idCoordinador);
        int idCoordLog = Integer.parseInt(idCoordinadorLogueado);

        Cursor cursor4  = baseDatosHelper.getPrioridad(idPrioridad, null);
        String ordenS = cursor4.getString(1);
        int orden = Integer.parseInt(ordenS);
        Log.e("REspuesta", ordenS);

        if (idCoord == idCoordLog)
        {

            holder.textViewSolicitudesHorario.setText(nomEvento);

            if(orden == 1)
            {
                Log.e("REspuesta ES 1", ordenS);
                holder.textViewRespuesta.setText(R.string.en_espera);
            }
            if(orden == 2)
            {
                holder.textViewRespuesta.setText(R.string.aprovado);
            }
            if(orden == 3)
            {
                holder.textViewRespuesta.setText(R.string.rechazado);
            }
            else{

            }

            // Set click listeners for buttons


        }else {
            holder.textViewRespuesta.setVisibility(View.GONE);

        }


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

    static class SolicitudesHorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSolicitudesHorario, textViewRespuesta;


        SolicitudesHorarioViewHolder(View itemView) {
            super(itemView);
            textViewSolicitudesHorario = itemView.findViewById(R.id.textViewSolicitudesHorario);
            textViewRespuesta = itemView.findViewById(R.id.textViewRespuesta);

        }
    }
}
