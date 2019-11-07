<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Http\Resources\ItemResource;
use App\Item;
use App\Sold;
use App\Buy;
use Illuminate\Http\Request;
use Carbon\Carbon;

class ItemController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        // $var = Item::find(6);
        // $var = Sold::where('name','MD')->first();
        // dd($var->item->quantity);
        return ItemResource::collection(Item::all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        if(Item::where('name',$request->name)->first()) return "Item Already Exists";
        else {
            Item::create($request->all());
            return "1";
        }
    }


    /**
     * Display the specified resource.
     *
     * @param  \App\Item  $item
     * @return \Illuminate\Http\Response
     */
    public function show($item)
    {
        $item = Item::where('name', $item)->orWhere('id',$item)->first();
        return new ItemResource($item);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Item  $item
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Item $item)
    {
        $item->update($request->all());
        return new ItemResource($item);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Item  $item
     * @return \Illuminate\Http\Response
     */
    public function destroy($item)
    {
        $item = Item::where('name', $item)->orWhere('id',$item)->first();
        return $item;
        if($item->delete())
        {
            return "1";
        }
        else "not found";
    }

    public function data(){
      
        // $todayDate = Carbon::today()->format('m-d-Y');
        $currentDate = Carbon::now()->startOfDay();
        $currentWeek = Carbon::now()->startOfDay()->subDays(7);
        $currentMonth = Carbon::now()->startOfDay()->subDays(30);
        $currentYear = Carbon::now()->startOfDay()->subDays(365);
        $todayProfit = Sold::where('created_at' ,'>', $currentDate)->sum('profit');
        $lastWeekProfit = Sold::where('created_at', '>', $currentWeek)->sum('profit');
        $lastMonthProfit = Sold::where('created_at', '>', $currentMonth)->sum('profit');
        $lastYearProfit = Sold::where('created_at', '>', $currentYear)->sum('profit');
        $totelProfit = Sold::sum('profit');


        $todayItemSold = Sold::where('created_at' ,'>', $currentDate)->sum('quantity');
        $lastWeekItemSold = Sold::where('created_at', '>', $currentWeek)->sum('quantity');
        $lastMonthItemSold = Sold::where('created_at', '>', $currentMonth)->sum('quantity');
        $lastYearItemSold = Sold::where('created_at', '>', $currentYear)->sum('quantity');
        $totelItemSold = Sold::sum('quantity');
        
        $todayItemBuy = Buy::where('created_at' ,'>', $currentDate)->sum('quantity');
        $lastWeekItemBuy = Buy::where('created_at', '>', $currentWeek)->sum('quantity');
        $lastMonthItemBuy = Buy::where('created_at', '>', $currentMonth)->sum('quantity');
        $lastYearItemBuy = Buy::where('created_at', '>', $currentYear)->sum('quantity');
        $totelItemBuy = Buy::sum('quantity');

        
        return [
            'profit' => [
                'todayProfit' => $todayProfit,
                'lastWeekProfit' => $lastWeekProfit,
                'lastMonthProfit' => $lastMonthProfit,
                'lastYearProfit' => $lastYearProfit,
                'totelProfit' => $totelProfit
            ],
            'itemSold' => [
                'todayItemSold' => $todayItemSold,
                'lastWeekItemSold' => $lastWeekItemSold,
                'lastMonthItemSold' => $lastMonthItemSold,
                'lastYearItemSold' => $lastYearItemSold,
                'totelItemSold' => $totelItemSold
            ],
            
            'itemBuy' => [
                'todayItemBuy' => $todayItemBuy,
                'lastWeekItemBuy' => $lastWeekItemBuy,
                'lastMonthItemBuy' => $lastMonthItemBuy,
                'lastYearItemBuy' => $lastYearItemBuy,
                'totelItemBuy' => $totelItemBuy
            ]
        ];
    }
}
