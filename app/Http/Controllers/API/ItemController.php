<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Http\Resources\ItemResource;
use App\Item;
use App\Sold;
use App\Buy;
use Illuminate\Http\Request;
use Carbon\Carbon;
use DB;
use Carbon\CarbonPeriod;

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
    public function update(Request $request, $item_name)
    {
        $item = Item::whereName($item_name)->first();
        if($item){
            $item->update($request->all());
             return new ItemResource($item);
        }
        else return "Item not found";
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

    public function itemCount(){
        return Item::all()->count();
    }

    public function dailyRecords(){
        $data = array();
        for($i = 0; $i < 10; $i++){
        $date = Carbon::now()->subDay($i)->isoFormat('YYYY-MM-DD');
        $date = Carbon::now()->subDay(0)->isoFormat('YYYY-MM-DD');
        $today = Carbon::now()->subDay($i)->isoFormat('DD-MM-YYYY');
        $Profit = Sold::whereDate('created_at', $date)->sum('profit');
        $soldItem = Sold::whereDate('created_at', $date)->sum('quantity');
        $buyItem = Buy::whereDate('created_at', $date)->sum('quantity');
        $web = array("sold" => $Profit, "soldItem" => $soldItem, "buyItem" => $buyItem);
        $data[$today] = $web;
    }
        return $data;
    }

    public function dailyData(){
        $data = array();

        $startingDate = Sold::select(DB::raw('DATE(`created_at`) as date'))->oldest()->first();
        $start = $startingDate->date;
        $date = Carbon::now()->subDay(0)->isoFormat('YYYY-MM-DD');

        $period = CarbonPeriod::create($start, $date);
        $periodCount = $period->count();
        
        for($i = 0 ; $i < $periodCount ; $i++){

            $date = Carbon::now()->subDay($i)->isoFormat('YYYY-MM-DD');
            $today = Carbon::now()->subDay($i)->isoFormat('DD-MM-YYYY');

            $customers = Sold::whereDate('created_at', $date)->select('name','customer','quantity','price_sold as price', DB::raw('TIME(`created_at`) as time'), DB::raw('DATE(`created_at`) as date'))->get();
            $customersBuy = Buy::whereDate('created_at', $date)->select('name', 'vender as customer', 'quantity', 'price_buy as price', DB::raw('TIME(`created_at`) as time'), DB::raw('DATE(`created_at`) as date'))->get();
            
            foreach($customers as $cs)
                 $cs->setAttribute('type','sold');
            foreach($customersBuy as $cb)
                 $cb->setAttribute('type','buy');

            $todayData = $customers->concat($customersBuy)->all();

            if($customers->isNotEmpty() || $customersBuy->isNotEmpty())
              $data[$today] = $todayData;
        
    }
        return $data;
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
