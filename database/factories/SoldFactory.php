<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Sold;
use Faker\Generator as Faker;

$factory->define(Sold::class, function (Faker $faker) {
    return [
        'name' => $faker->name,
        'customer' => $faker->name,
        'price_sold' => $faker->randomDigit,
        'profit' => $faker->randomDigit,
        'quantity' => $faker->randomDigit,
        'balance_paid' => $faker->randomDigit,
        'balance_required' => $faker->randomDigit,
        'created_at' => $faker->date
    ];
});
