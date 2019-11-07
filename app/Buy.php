<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Buy extends Model

{
    
    // protected $attributes = [
    //     'profit' => 2,
    // ];
    protected $guarded = [];

    public function item(){
        return $this->belongsTo('App\Item','name','name');
    }

}
