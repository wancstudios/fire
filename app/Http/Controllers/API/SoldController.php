<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Sold;
use App\Item;
use Illuminate\Http\Request;
use App\Http\Resources\SoldResource;

class SoldController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        // $var = Sold::first();
        // dd($var->item->price);
        return SoldResource::collection(Sold::all());
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
        $price = $item->price;
        $profit = ($request->price_sold - $price) * $request->quantity;
        $balance_required = ($request->price_sold - $request->balance_paid);

        if($item->quantity - $request->quantity < 0) return "That much items are not available";

        $var = Sold::create([
            'name' => $request->name,
            'customer' => $request->customer,
            'quantity' => $request->quantity,
            'price_sold' => $request->price_sold,
            'profit' => $profit,
            'balance_paid' => $request->balance_paid,
            'balance_required' => $balance_required
            ]);

        Item::where('name', $request->name)->decrement('quantity',$request->quantity);
        if($var) return "1";
        else return "0";
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Sold  $sold
     * @return \Illuminate\Http\Response
     */
    public function show(Sold $sold)
    {
        // return response()->json(Sold::find($sold));
        return new SoldResource($sold);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Sold  $sold
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Sold $sold)
    {
        
        $balance_required = $sold->balance_required - $request->balance_paid; 
        if($balance_required < 0){
            return "not that much amount required";
        }
        $balance_paid = $sold->balance_paid + $request->balance_paid;
        
        
        $sold->update([
            'balance_paid' => $balance_paid,
            'balance_required' => $balance_required
        ]);
        return "1";
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Sold  $sold
     * @return \Illuminate\Http\Response
     */
    public function destroy(Sold $sold)
    {
        $sold->delete();
        return response(null);
    }
}
