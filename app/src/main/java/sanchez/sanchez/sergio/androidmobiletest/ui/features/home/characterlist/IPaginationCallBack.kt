package sanchez.sanchez.sergio.androidmobiletest.ui.features.home.characterlist

interface IPaginationCallBack {

    /**
     * on Load Next Page
     */
    fun onLoadNextPage()

    /**
     * Is Pagination Enabled
     */
    fun isPaginationEnabled(): Boolean
}