<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});


Route::get("/test",'Controller@test');


Route::apiResource('/item','API\ItemController');
Route::apiResource('/buy','API\BuyController');
Route::apiResource('/sold','API\SoldController');

Route::get('/data','API\ItemController@data');
Route::get('/itemCount','API\ItemController@itemCount');
Route::get('/dailyRecords','API\ItemController@dailyRecords');
Route::get('/dailyData','API\ItemController@dailyData');