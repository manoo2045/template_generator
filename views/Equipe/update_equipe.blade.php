@extends('template.admin_home')

@section('import-css')
    <link href=""{{ asset('assets/libs/bootstrap/dist/css/bootstrap.min.css') }}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/libs/select2/dist/css/select2.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/css/table.css') }}">
@endsection

@section('content')
    <div class="card-body">
        <h5 class="card-title fw-semibold mb-4">Modifier equipe</h5>
        <div class="col-md-5">
            <div class="card">
                <div class="card-body">
                    <form action="{{ url('/equipe/update') }}" method="post" >
                        @csrf
                            <input type="hidden" value="{{ $equipe->id }}" name="id">
                            		<div class="mb-3">
			<label for="exampleInput_nom" class="form-label">nom</label>
			<input name="nom" value="{{ $equipe->nom }}" type="text" class="form-control @error("nom") is-invalid @enderror" id="exampleInput_nom" aria-describedby="emailHelp">
			@error("nom")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_pwd" class="form-label">pwd</label>
			<input name="pwd" value="{{ $equipe->pwd }}" type="text" class="form-control @error("pwd") is-invalid @enderror" id="exampleInput_pwd" aria-describedby="emailHelp">
			@error("pwd")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_login" class="form-label">login</label>
			<input name="login" value="{{ $equipe->login }}" type="text" class="form-control @error("login") is-invalid @enderror" id="exampleInput_login" aria-describedby="emailHelp">
			@error("login")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>


                            <div class="modal-footer">
                                <button type="submit" class="btn btn-info waves-effect">Effectue</button>
                                <a href="{{ url('/equipe') }}" type="button" class="btn btn-danger waves-effect" data-dismiss="modal">Cancel</a>
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
