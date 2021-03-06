package art.coded.spotpost.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.spotpost.R;
import art.coded.spotpost.databinding.FragmentListBinding;
import art.coded.spotpost.view.adapter.ListAdapter;
import art.coded.spotpost.viewmodel.ListViewModel;

/**
 * Manages the UI visuals and interactions inside the content view of ListActivity
 */
public class ListFragment extends Fragment {

    private static final String LOG_TAG = ListFragment.class.getSimpleName();

    // Member variables
    private ListViewModel listViewModel;
    private FragmentListBinding binding;

    // Inflates Views and defines UI objects and their interactions
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate root view
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get Activity reference for context
        Activity activity = getActivity();

        // Instantiate amd format RecyclerView and attach ListAdapter to RecyclerView
        final RecyclerView recyclerView = binding.rvList;
        ListAdapter listAdapter = new ListAdapter(activity);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        // Instantiate and load ViewModel
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.load(requireContext());

        // Populate ListAdapter with observable entity LiveData generating callbacks on list updates
        listViewModel.getData().observe(getViewLifecycleOwner(),
                entityList -> listAdapter.setEvents(entityList));

        return root;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                NavHostFragment.findNavController(ListFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    // Nullify view bindings on Fragment destruction
    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}