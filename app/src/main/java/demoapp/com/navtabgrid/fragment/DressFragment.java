package demoapp.com.navtabgrid.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;

import demoapp.com.navtabgrid.R;
import demoapp.com.navtabgrid.WooCommerceApi;
import demoapp.com.navtabgrid.adapter.ItemAdapter;
import demoapp.com.navtabgrid.model.Item;
import demoapp.com.navtabgrid.utils.ItemClickSupport;

public class DressFragment extends Fragment {
    //Defining Variables
    ArrayList<Item> homeItem;
    RecyclerView mRecyclerView;
    ItemAdapter adapter;
    String mLocationName;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    ProgressBar mProgress;
    RelativeLayout mLoadingProgress;


    String imageID, ImageUrl;

    public static DressFragment newInstance() {
        DressFragment fragment = new DressFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public DressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dress_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view initialize and functionality declare

        homeItem = new ArrayList<Item>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.homeItemRecycler);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mLoadingProgress = (RelativeLayout) view.findViewById(R.id.loading_progress);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        adapter = new ItemAdapter(getActivity(), homeItem);
        mRecyclerView.setAdapter(adapter);
        //new ProductAsyncTask().execute();

        // Select item on listclick
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Item data = homeItem.get(position);
                        mLocationName = data.getRentPrice();
                        Log.d("Location: ", mLocationName);

                            /*Intent intent = new Intent(getActivity(), contact_profile.class);
                            intent.putExtra("display_name", mDisplayName);
                            intent.putExtra("mobileNumber", mMobileNumber);
                            intent.putExtra("BitmapImage", bitMap_parse);
                            getActivity().startActivity(intent);*/
                    }
                }
        );


    }

    private class ProductAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //Create progress dialog here and show it
            mLoadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String restURL = "http://www.reveriegroup.com/demo/wp-json/wc/v1/products?per_page=12";
            OAuthService service = new ServiceBuilder()
                    .provider(WooCommerceApi.class)
                    .apiKey("ck_8e344e1ef42a00f4d4c3bb1ede1557d48790c322") //Your Consumer key
                    .apiSecret("cs_09de5a54244811bc1291ebb4a4d4b7b6725c4cec") //Your Consumer secret
                    .scope("API.Public") //fixed
                    .signatureType(SignatureType.QueryString)
                    .build();
            OAuthRequest request = new OAuthRequest(Verb.GET, restURL);
            Token accessToken = new Token("", ""); //not required for context.io
            service.signRequest(accessToken, request);
            Response response = request.send();
            String data = response.getBody();
            Log.d("OAuthTask", response.getBody());
            try {
                parseJSON(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //update your listView adapter here
            //Dismiss your dialog
            mLoadingProgress.setVisibility(View.GONE);
            adapter = new ItemAdapter(getActivity(), homeItem);
            mRecyclerView.setAdapter(adapter);

        }
    }

    public void parseJSON(String data) throws JSONException {
        JSONArray jsonArr = new JSONArray(data);
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            String id = jsonObj.getString("id").toString();
            String name = jsonObj.getString("name");
            String price = jsonObj.getString("price");
            String subdata = jsonObj.getString("images");
            JSONArray json_data1 = new JSONArray(subdata);
            for (int j = 0; j < json_data1.length(); j++) {
                jsonObj = json_data1.getJSONObject(j);
                imageID = jsonObj.getString("id");
                ImageUrl = jsonObj.getString("src");
            }
            Log.d("Products Details: ", "ID:: " + id + " Name:: " + name + " Price:: " + price + " ImageID:: " + imageID + " ImageUrl:: " + ImageUrl);
            Item productItem = new Item();
            productItem.setImageUrl(ImageUrl);
            productItem.setRentPrice(price);
            homeItem.add(productItem);
        }
    }


}
