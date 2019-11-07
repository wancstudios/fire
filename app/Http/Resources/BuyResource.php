<?php

namespace App\Http\Resources;


use Illuminate\Http\Resources\Json\JsonResource;

class BuyResource extends JsonResource
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
        return [
            'name' => $this->name,
            'vender' => $this->vender,
            'quantity' => $this->quantity,
            'price_buy' => $this->price_buy,
            // 'profit' => $this->profit,
            'date' => $this->created_at->format('d-m-Y'),
        ];

    }
}
