package com.dmribeiro87.cupcakeapp.ui.home

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.model.Cupcake
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class HomeFragmentDirections private constructor() {
  private data class ActionNavHomeToDetailsFragment(
    public val selectedCupcake: Cupcake,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_nav_home_to_detailsFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
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
  }

  public companion object {
    public fun actionNavHomeToDetailsFragment(selectedCupcake: Cupcake): NavDirections =
        ActionNavHomeToDetailsFragment(selectedCupcake)
  }
}
