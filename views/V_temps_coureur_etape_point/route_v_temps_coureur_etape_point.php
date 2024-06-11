Route::get('/v_temps_coureur_etape_point',[V_temps_coureur_etape_pointController::class,'list']);
Route::get('/v_temps_coureur_etape_point/list/',[V_temps_coureur_etape_pointController::class,'list']);
Route::post('/v_temps_coureur_etape_point/insert',[V_temps_coureur_etape_pointController::class,'insert']);
Route::get('/v_temps_coureur_etape_point/edit/{id}',[V_temps_coureur_etape_pointController::class,'v_temps_coureur_etape_pointEditView']);
//Route::get('/v_temps_coureur_etape_point/insert',[V_temps_coureur_etape_pointController::class,'v_temps_coureur_etape_pointInsertView']);
Route::post('/v_temps_coureur_etape_point/update',[V_temps_coureur_etape_pointController::class,'v_temps_coureur_etape_pointEdit']);
Route::get('/v_temps_coureur_etape_point/delete/{id}',[V_temps_coureur_etape_pointController::class,'delete']);


<li class="sidebar-item">
    <a href="{{ url('/v_temps_coureur_etape_point/list') }}" class="sidebar-link">
        <i class="mdi mdi-av-timer"></i>
        <span class="hide-menu">v_temps_coureur_etape_point</span>
    </a>
</li>

