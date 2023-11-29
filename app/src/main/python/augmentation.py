import Augmentor

def augment_images(input_path, output_path, num_samples):
    # Create an Augmentor pipeline
    p = Augmentor.Pipeline(input_path, output_directory=output_path)

    # Define augmentation operations
    p.zoom(probability=0.3, min_factor=0.8, max_factor=1.5)
    p.flip_top_bottom(probability=0.4)
    p.random_brightness(probability=0.3, min_factor=0.3, max_factor=1.2)
    p.random_distortion(probability=1, grid_width=4, grid_height=4, magnitude=8)

    # Sample 'num_samples' augmented images
    p.sample(num_samples)

if __name__ == "__main__":
    pass  # Nothing additional needed in this section
