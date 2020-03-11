<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ItemResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        // return parent::toArray($request);
        return[
            'name' => $this->name, 
            'price' => $this->price,
            'quantity' => $this->quantity,
            // 'sold_detail' => SoldResource::collection($this->sold), 
            // 'profit' => SoldResource::collection($this->sold)->sum('profit'),
        ];
    }

    // public function with($request){
    //     return[
    //         'author' => 'rizzu1023',
    //         'function' => 'with',
    //     ];
    // }
}

