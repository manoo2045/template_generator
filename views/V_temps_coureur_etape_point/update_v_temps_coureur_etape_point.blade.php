@extends('template.admin_home')

@section('import-css')
    <link href=""{{ asset('assets/libs/bootstrap/dist/css/bootstrap.min.css') }}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/libs/select2/dist/css/select2.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/css/table.css') }}">
@endsection

@section('content')
    <div class="card-body">
        <h5 class="card-title fw-semibold mb-4">Modifier v_temps_coureur_etape_point</h5>
        <div class="col-md-5">
            <div class="card">
                <div class="card-body">
                    <form action="{{ url('/v_temps_coureur_etape_point/update') }}" method="post" >
                        @csrf
                            <input type="hidden" value="{{ $v_temps_coureur_etape_point->id }}" name="id">
                            		<div class="mb-3">
			<label for="exampleInput_id_etape" class="form-label">id_etape</label>
			<input name="id_etape" value="{{ $v_temps_coureur_etape_point->id_etape }}" type="number" class="form-control @error("id_etape") is-invalid @enderror" id="exampleInput_id_etape" aria-describedby="emailHelp">
			@error("id_etape")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_depart" class="form-label">depart</label>
			<input name="depart" value="{{ $v_temps_coureur_etape_point->depart }}" type="" class="form-control @error("depart") is-invalid @enderror" id="exampleInput_depart" aria-describedby="emailHelp">
			@error("depart")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_heure_arrive" class="form-label">heure_arrive</label>
			<input name="heure_arrive" value="{{ $v_temps_coureur_etape_point->heure_arrive }}" type="" class="form-control @error("heure_arrive") is-invalid @enderror" id="exampleInput_heure_arrive" aria-describedby="emailHelp">
			@error("heure_arrive")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_id_coureur" class="form-label">id_coureur</label>
			<input name="id_coureur" value="{{ $v_temps_coureur_etape_point->id_coureur }}" type="number" class="form-control @error("id_coureur") is-invalid @enderror" id="exampleInput_id_coureur" aria-describedby="emailHelp">
			@error("id_coureur")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_penalite" class="form-label">penalite</label>
			<input name="penalite" value="{{ $v_temps_coureur_etape_point->penalite }}" type="number" class="form-control @error("penalite") is-invalid @enderror" id="exampleInput_penalite" aria-describedby="emailHelp">
			@error("penalite")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_temps_effectue" class="form-label">temps_effectue</label>
			<input name="temps_effectue" value="{{ $v_temps_coureur_etape_point->temps_effectue }}" type="number" class="form-control @error("temps_effectue") is-invalid @enderror" id="exampleInput_temps_effectue" aria-describedby="emailHelp">
			@error("temps_effectue")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_rang" class="form-label">rang</label>
			<input name="rang" value="{{ $v_temps_coureur_etape_point->rang }}" type="" class="form-control @error("rang") is-invalid @enderror" id="exampleInput_rang" aria-describedby="emailHelp">
			@error("rang")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_point" class="form-label">point</label>
			<input name="point" value="{{ $v_temps_coureur_etape_point->point }}" type="number" class="form-control @error("point") is-invalid @enderror" id="exampleInput_point" aria-describedby="emailHelp">
			@error("point")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_id_course" class="form-label">id_course</label>
			<input name="id_course" value="{{ $v_temps_coureur_etape_point->id_course }}" type="number" class="form-control @error("id_course") is-invalid @enderror" id="exampleInput_id_course" aria-describedby="emailHelp">
			@error("id_course")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>


                            <div class="modal-footer">
                                <button type="submit" class="btn btn-info waves-effect">Effectue</button>
                                <a href="{{ url('/v_temps_coureur_etape_point') }}" type="button" class="btn btn-danger waves-effect" data-dismiss="modal">Cancel</a>
                            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
@endsection


@section('import-js')
    <script src="{{ asset('assets/libs/select2/dist/js/select2.full.min.js') }}"></script>
    <script src="{{ asset('assets/libs/select2/dist/js/select2.min.js') }}"></script>
    <script src="{{ asset('dist/js/pages/forms/select2/select2.init.js') }}"></script>
@endsection
