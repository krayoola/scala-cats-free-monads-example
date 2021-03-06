/*
 * Copyright 2016 Sebastian Wiesner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lunaryorn.weather.free

import cats.~>
import com.lunaryorn.weather.TemperatureRepository
import com.lunaryorn.weather.free.dsl.types.TemperatureAction
import com.lunaryorn.weather.free.dsl.types.TemperatureAction._
import com.twitter.util.Future

object Interpreters {

  def interpretTemperatureActionWithRepository(
      repo: TemperatureRepository
  ): TemperatureAction ~> Future =
    new (TemperatureAction ~> Future) {
      override def apply[A](action: TemperatureAction[A]): Future[A] =
        action match {
          case GetAll => repo.getTemperatures
          case Store(temperature) => repo.addTemperature(temperature)
        }
    }
}
