<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Temps_coureur extends Model
{
    use HasFactory;

    public static function getTemps_coureurById($id){
        return DB::table('temps_coureur')
            ->where('id',$id)
            ->first();
    }

    public function getAllTemps_coureur() {
        return DB::table('temps_coureur')
            ->get();
    }


}
