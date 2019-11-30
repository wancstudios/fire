<?php

namespace App\Http\Controllers\API;

use App\Buy;
use App\Item;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Http\Resources\BuyResource;


class BuyController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(Buy::all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

        $item = Item::where('name', $request->name)->first();
        if(!$item)  return "Item Not Found";

        $var = Buy::create([
            'name' => $request->name,
            'vender' => $request->vender,
            'quantity' => $request->quantity,
            'price_buy' => $request->price_buy,
            'profit' => 0
            ]);

        if($var){
          Item::where('name', $request->name)->increment('quantity',$request->quantity);
          Item::where('name', $request->name)->update(['price'=>$request->price_buy]);
          return "1";
        }
        else return "0";

    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Buy  $buy
     * @return \Illuminate\Http\Response
     */
    public function show(Buy $buy)
    {
        // return response()->json(Buy::find($buy));
        return new BuyResource($buy);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Buy  $buy
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Buy $buy)
    {
        $buy->update($request->all());
        return response()->json($buy);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Buy  $buy
     * @return \Illuminate\Http\Response
     */
    public function destroy(Buy $buy)
    {
        $buy->delete();
        return response(null);
    }
}
