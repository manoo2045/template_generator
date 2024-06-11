<?php

namespace App\Http\Controllers;

use App\Models\V_temps_coureur_etape_point;
use App\Models\Finition;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class V_temps_coureur_etape_pointController extends Controller
{
    public function list() {
        $obj = new V_temps_coureur_etape_point();
        $v_temps_coureur_etape_point = $obj->getAllV_temps_coureur_etape_point();
        return view('v_temps_coureur_etape_point.list_v_temps_coureur_etape_point',[
            'v_temps_coureur_etape_points' => $v_temps_coureur_etape_point
        ]);
    }

//    public function v_temps_coureur_etape_pointInsertView() {
//        return view('v_temps_coureur_etape_point.insert_v_temps_coureur_etape_point');
//    }

    public function insert(Request $request){
        $request->validate(
        [
		'id_etape'=> ['required'],
		'depart'=> ['required'],
		'heure_arrive'=> ['required'],
		'id_coureur'=> ['required'],
		'penalite'=> ['required'],
		'temps_effectue'=> ['required'],
		'rang'=> ['required'],
		'point'=> ['required'],
		'id_course'=> ['required'],
	]
        );
        try {
            $id = DB::table('v_temps_coureur_etape_point')->insertGetId([
		'id_etape'=> $request->id_etape,
		'depart'=> $request->depart,
		'heure_arrive'=> $request->heure_arrive,
		'id_coureur'=> $request->id_coureur,
		'penalite'=> $request->penalite,
		'temps_effectue'=> $request->temps_effectue,
		'rang'=> $request->rang,
		'point'=> $request->point,
		'id_course'=> $request->id_course,
	],'id');
            return back()->with('message',['Inserer v_temps_coureur_etape_point avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function v_temps_coureur_etape_pointEditView($id) {
        $v_temps_coureur_etape_point = V_temps_coureur_etape_point::getV_temps_coureur_etape_pointById($id);

        return view('v_temps_coureur_etape_point.update_v_temps_coureur_etape_point',[
            'v_temps_coureur_etape_point' => $v_temps_coureur_etape_point
        ]);
    }

    public function v_temps_coureur_etape_pointEdit(Request $request) {
        $request->validate(
        [
		'id_etape'=> ['required'],
		'depart'=> ['required'],
		'heure_arrive'=> ['required'],
		'id_coureur'=> ['required'],
		'penalite'=> ['required'],
		'temps_effectue'=> ['required'],
		'rang'=> ['required'],
		'point'=> ['required'],
		'id_course'=> ['required'],
	]
        );

        try {
            DB::table('v_temps_coureur_etape_point')
                ->where('id', $request->id)
                ->update(
                    [
		'id_etape'=> $request->id_etape,
		'depart'=> $request->depart,
		'heure_arrive'=> $request->heure_arrive,
		'id_coureur'=> $request->id_coureur,
		'penalite'=> $request->penalite,
		'temps_effectue'=> $request->temps_effectue,
		'rang'=> $request->rang,
		'point'=> $request->point,
		'id_course'=> $request->id_course,
	]
                );

            return redirect('/v_temps_coureur_etape_point')->with('message',['Modier avec succes']);

        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function delete($id)
    {
        try {
            DB::table('v_temps_coureur_etape_point')
                ->where('id',$id)
                ->delete();
            return back()->with('success',['Supprimer avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

}
