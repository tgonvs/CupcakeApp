package com.dmribeiro87.cupcakeapp.ui.details

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.dmribeiro87.model.Cupcake
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class DetailsFragmentArgs(
  public val selectedCupcake: Cupcake,
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
    val result = Bundle()
    if (Parcelable::class.java.isAssignableFrom(Cupcake::class.java)) {
      result.putParcelable("selectedCupcake", this.selectedCupcake as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Cupcake::class.java)) {
      result.putSerializable("selectedCupcake", this.selectedCupcake as Serializable)
    } else {
      throw UnsupportedOperationException(Cupcake::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(Cupcake::class.java)) {
      result.set("selectedCupcake", this.selectedCupcake as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Cupcake::class.java)) {
      result.set("selectedCupcake", this.selectedCupcake as Serializable)
    } else {
      throw UnsupportedOperationException(Cupcake::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): DetailsFragmentArgs {
      bundle.setClassLoader(DetailsFragmentArgs::class.java.classLoader)
      val __selectedCupcake : Cupcake?
      if (bundle.containsKey("selectedCupcake")) {
        if (Parcelable::class.java.isAssignableFrom(Cupcake::class.java) ||
            Serializable::class.java.isAssignableFrom(Cupcake::class.java)) {
          __selectedCupcake = bundle.get("selectedCupcake") as Cupcake?
        } else {
          throw UnsupportedOperationException(Cupcake::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__selectedCupcake == null) {
          throw IllegalArgumentException("Argument \"selectedCupcake\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"selectedCupcake\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__selectedCupcake)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DetailsFragmentArgs {
      val __selectedCupcake : Cupcake?
      if (savedStateHandle.contains("selectedCupcake")) {
        if (Parcelable::class.java.isAssignableFrom(Cupcake::class.java) ||
            Serializable::class.java.isAssignableFrom(Cupcake::class.java)) {
          __selectedCupcake = savedStateHandle.get<Cupcake?>("selectedCupcake")
        } else {
          throw UnsupportedOperationException(Cupcake::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__selectedCupcake == null) {
          throw IllegalArgumentException("Argument \"selectedCupcake\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"selectedCupcake\" is missing and does not have an android:defaultValue")
      }
      return DetailsFragmentArgs(__selectedCupcake)
    }
  }
}
