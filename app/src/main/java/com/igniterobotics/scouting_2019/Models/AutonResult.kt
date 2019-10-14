package com.igniterobotics.scouting_2019.Models

import android.os.Parcel
import android.os.Parcelable
import com.igniterobotics.scouting_2019.Enums.Movement
import com.igniterobotics.scouting_2019.Enums.Preload
import com.igniterobotics.scouting_2019.Enums.StartingPosition

class AutonResult() : Parcelable {
    var hatchCount = 0
    var cargoCount = 0
    var intakeDrops = 0
    var drops = 0

    var startingPosition = 0
    var preload = 0
    var movement = 0

    constructor(parcel: Parcel) : this() {
        hatchCount = parcel.readInt()
        cargoCount = parcel.readInt()
        intakeDrops = parcel.readInt()
        drops = parcel.readInt()
        startingPosition = parcel.readInt()
        preload = parcel.readInt()
        movement = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(hatchCount)
        parcel.writeInt(cargoCount)
        parcel.writeInt(intakeDrops)
        parcel.writeInt(drops)
        parcel.writeInt(startingPosition)
        parcel.writeInt(preload)
        parcel.writeInt(movement)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AutonResult> {
        override fun createFromParcel(parcel: Parcel): AutonResult {
            return AutonResult(parcel)
        }

        override fun newArray(size: Int): Array<AutonResult?> {
            return arrayOfNulls(size)
        }
    }


}