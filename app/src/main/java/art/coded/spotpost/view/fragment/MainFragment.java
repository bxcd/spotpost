package art.coded.spotpost.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import art.coded.spotpost.R;
import art.coded.spotpost.databinding.FragmentMainBinding;
import art.coded.spotpost.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(v -> {
            MainViewModel mainViewModel = new ViewModelProvider(
                    requireActivity()).get(MainViewModel.class);
            mainViewModel.load(getParentFragment().requireContext());
            mainViewModel.spotPost();
            Snackbar.make(view, "Processing request...", Snackbar.LENGTH_LONG).show();
        });

        binding.buttonFirst.setOnClickListener(
                view1 -> NavHostFragment.findNavController(MainFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}