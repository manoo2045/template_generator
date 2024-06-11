<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class V_temps_coureur_etape_point extends Model
{
    use HasFactory;

    public static function getV_temps_coureur_etape_pointById($id){
        return DB::table('v_temps_coureur_etape_point')
            ->where('id',$id)
            ->first();
    }

    public function getAllV_temps_coureur_etape_point() {
        return DB::table('v_temps_coureur_etape_point')
            ->get();
    }


}
