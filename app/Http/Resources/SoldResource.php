<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class SoldResource extends JsonResource
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
            'customer' => $this->customer,
            'quantity' => $this->quantity,
            'price_sold' => $this->price_sold,
            'profit' => $this->profit,
            'date' => $this->created_at->format('d-m-Y'),
            // 'date' => $this->date_sold,
            // 'quantityItem' => $this->item->quantity,
            // 'price' => $this->item->price,
        ];
    }
}
