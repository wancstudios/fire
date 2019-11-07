<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Item extends Model
{
    protected $guarded =  [];

    public function sold(){
        return $this->hasMany('App\Sold', 'item_id','id');   
    }

    public function buy(){
        return $this->hasMany('App\Buy', 'item_id','id');   
    }
}
