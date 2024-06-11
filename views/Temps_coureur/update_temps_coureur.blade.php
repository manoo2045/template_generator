@extends('template.admin_home')

@section('import-css')
    <link href=""{{ asset('assets/libs/bootstrap/dist/css/bootstrap.min.css') }}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/libs/select2/dist/css/select2.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/css/table.css') }}">
@endsection

@section('content')
    <div class="card-body">
        <h5 class="card-title fw-semibold mb-4">Modifier temps_coureur</h5>
        <div class="col-md-5">
            <div class="card">
                <div class="card-body">
                    <form action="{{ url('/temps_coureur/update') }}" method="post" >
                        @csrf
                            <input type="hidden" value="{{ $temps_coureur->id }}" name="id">
                            		<div class="mb-3">
			<label for="exampleInput_heure_depart" class="form-label">heure_depart</label>
			<input name="heure_depart" value="{{ $temps_coureur->heure_depart }}" type="" class="form-control @error("heure_depart") is-invalid @enderror" id="exampleInput_heure_depart" aria-describedby="emailHelp">
			@error("heure_depart")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_heure_arrive" class="form-label">heure_arrive</label>
			<input name="heure_arrive" value="{{ $temps_coureur->heure_arrive }}" type="" class="form-control @error("heure_arrive") is-invalid @enderror" id="exampleInput_heure_arrive" aria-describedby="emailHelp">
			@error("heure_arrive")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_penalite" class="form-label">penalite</label>
			<input name="penalite" value="{{ $temps_coureur->penalite }}" type="number" class="form-control @error("penalite") is-invalid @enderror" id="exampleInput_penalite" aria-describedby="emailHelp">
			@error("penalite")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_id_etape class="form-label">nom</label>
			<select name="id_etape" class="select2 form-control custom-select @error('id_etape') is-invalid @enderror" style="width: 100%; height:36px;" id="exampleInput_id_etape" aria-describedby="temps_coureurlHelp">
			@foreach($ids as $id)
				 <option value="{{ $nom->id }}" @if($id_etape->id === $temps_coureur->nom) selected @endif >{{ $id->nom }}</option> 
			@endforeach
			</select>
		</div>
		<div class="mb-3">
			<label for="exampleInput_id_coureur class="form-label">nom</label>
			<select name="id_coureur" class="select2 form-control custom-select @error('id_coureur') is-invalid @enderror" style="width: 100%; height:36px;" id="exampleInput_id_coureur" aria-describedby="temps_coureurlHelp">
			@foreach($ids as $id)
				 <option value="{{ $nom->id }}" @if($id_coureur->id === $temps_coureur->nom) selected @endif >{{ $id->nom }}</option> 
			@endforeach
			</select>
		</div>

                            <div class="modal-footer">
                                <button type="submit" class="btn btn-info waves-effect">Effectue</button>
                                <a href="{{ url('/temps_coureur') }}" type="button" class="btn btn-danger waves-effect" data-dismiss="modal">Cancel</a>
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
