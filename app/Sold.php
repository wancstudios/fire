<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Sold extends Model
{
    protected $guarded = [];

    // protected $dateFormat = 'd-m-Y';

    public function item(){
        return $this->belongsTo('App\Item','name','name');
    }
}
